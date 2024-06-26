package org.voidlang.compiler.node.hierarchy;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.voidlang.compiler.ast.Node;
import org.voidlang.compiler.node.Generator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * Represents a utility class that visits the node hierarchy and initializes the parent-child relationships.
 *
 * @see Children
 * @see Parent
 */
@UtilityClass
public class NodeVisitor {
    /**
     * The set of nodes that are registered for the node hierarchy.
     */
    private final @NotNull Set<@NotNull Node> NODES = new CopyOnWriteArraySet<>();

    /**
     * Register the node to the node hierarchy.
     *
     * @param node the node to register
     */
    public void register(@NotNull Node node) {
        NODES.add(node);
    }

    /**
     * Initialize the parent-child relationships for the node hierarchy.
     */
    public void initHierarchy() {
        // iterate each node registered in the hierarchy
        for (Node node : NODES) {
            // iterate each field of the node, including the inherited fields
            for (Field field : getFields(node.getClass())) {
                // check if the field is annotated with the children annotation
                if (!field.isAnnotationPresent(Children.class))
                    continue;

                // process the children field of the node
                field.setAccessible(true);
                processField(node, field);
            }
        }
    }

    /**
     * Initialize the lifecycle of each node in the node hierarchy.
     *
     * @param generator the LLVM code generation context
     */
    public void initLifecycle(@NotNull Generator generator) {
        visit(Node::init);
        visit(node -> node.initTypes(generator));
        visit(node -> node.initMembers(generator));
        visit(node -> node.initUses(generator));
    }

    /**
     * Recursively visit each node in the node hierarchy, and apply the {@param visitor} function to each node.
     * <p>
     * The nodes are visited in the depth-first order, starting from the root node and recursively visiting each
     * child node of the parent node.
     *
     * @param visitor the function to apply to each node
     */
    public void visit(@NotNull Consumer<@NotNull Node> visitor) {
        visitChildren(NODES, visitor);
    }

    /**
     * Recursively visit each node in the {@param nodes} list, and apply the {@param visitor} function to each node.
     * <p>
     * The nodes are visited in the depth-first order, starting from the root node and recursively visiting each
     * child node of the parent node.
     *
     * @param nodes the list of nodes to visit
     * @param visitor the function to apply to each node
     */
    private void visitChildren(@NotNull Set<@NotNull Node> nodes, @NotNull Consumer<@NotNull Node> visitor) {
        for (Node node : nodes) {
            visitor.accept(node);
            visitChildren(node.children(), visitor);
        }
    }

    /**
     * Process a field of the node that is annotated with {@link Children}.
     *
     * @param parent the node that contains the children field
     * @param field the children field to process
     */
    private void processField(@NotNull Node parent, @NotNull Field field) {
        // try to resolve the value of the children field
        Object children;
        try {
            children = field.get(parent);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot access children field", e);
        }

        // handle single-children and case
        if (children instanceof Node child)
            processParent(parent, child);

        // handle multi-children case
        else if (children instanceof Iterable<?>) {
            for (Object element : (Iterable<?>) children) {
                if (!(element instanceof Node child))
                    throw new IllegalStateException(
                        "Children field `" + field.getName() + "` of node `" + parent +
                        "` must be a node or a list of nodes"
                    );
                processParent(parent, child);
            }
        }

        else
            throw new IllegalStateException(
                "Children field `" + field.getName() + "` of node `" + parent +
                "` must be a node or a list of nodes, not " + children
            );
    }

    /**
     * Initialize each field annotated with {@link Parent} of the {@param child} node with the {@code parent} node.
     *
     * @param parent the parent node
     * @param child the child node
     */
    private void processParent(@NotNull Node parent, @NotNull Node child) {
        // iterate each field of the child node, including the inherited fields
        for (Field field : getFields(child.getClass())) {
            // check if the field is annotated with the parent annotation
            if (!field.isAnnotationPresent(Parent.class))
                continue;

            // assign the parent node to the field
            field.setAccessible(true);
            try {
                field.set(child, parent);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot access parent field", e);
            }
        }
    }

    /**
     * Clear each node registered in the node hierarchy.
     */
    public void clear() {
        NODES.clear();
    }

    /**
     * Resolve the declared and inherited fields of the specified {@param type}.
     *
     * @param type the type to resolve the fields for
     * @return the list of declared and inherited fields of the type
     */
    public @NotNull List<@NotNull Field> getFields(@NotNull Class<?> type) {
        List<Field> fields = new ArrayList<>();
        while (type != null) {
            Collections.addAll(fields, type.getDeclaredFields());
            type = type.getSuperclass();
        }
        return fields;
    }
}
