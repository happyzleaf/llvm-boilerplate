#include "Node.hpp"

namespace Compiler {
    OutputStream& operator<<(OutputStream& stream, NodeType& type) {
        static const char* const NAMES[] = {
            "Method",
            "Package",
            "Import",
            "Type",
            "Local",
            "SingleValue",
            "Operation",
            "Error",
            "Finish"
        };
        return stream << NAMES[static_cast<int>(type)];
    }

    /**
     * Initialize the node.
     * @param type node type
     */
    Node::Node(NodeType type)
        : type(type)
    { }

    /**
     * Debug the content of the parsed node.
     */
    void Node::debug() {
        print("None");
    }

    /**
     * Initialize the error node.
     */
    ErrorNode::ErrorNode()
        : Node(NodeType::Error)
    { }

    /**
     * Debug the content of the parsed node.
     */
    void ErrorNode::debug() {
        print("Error");
    }

    /**
     * Initialize the finish node.
     */
    FinishNode::FinishNode()
        : Node(NodeType::Finish)
    { }

    /**
     * Debug the content of the parsed node.
     */
    void FinishNode::debug() {
        print("Finish");
    }
}