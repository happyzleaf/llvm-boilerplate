#pragma once

// string manipulation

#define NOMINMAX

#include <iostream>
#include <string>

typedef std::string String;

// maps

#include <unordered_map>

template <typename K, typename V>
using Map = std::unordered_map<K, V>;

// lists

template <typename T>
using List = std::vector<T>;

#define contains(list, element) \
    std::find(list.begin(), list.end(), element) != list.end()

// debugging

#define println(x) \
    std::cout << x << '\n'

#define print(x) \
    std::cout << x

#define error(x) \
    { std::cout << x << '\n'; exit(-1); }

#define warn(x) \
    std::cout << "[Warning] " << x << '\n'

// number conversion 

#define stringToInt std::stoi

// funcations

#include <functional>

template <typename T>
using Function = std::function<T>;
// #define Function std::function

// files

#include <filesystem>

#define FS std::filesystem

#define fileIsDirectory FS::is_directory
#define fileExists FS::exists

typedef FS::path Path;

#include <fstream>

typedef std::ifstream FileReader;
typedef std::ofstream FileWriter;

typedef FS::directory_iterator DirIterator;
typedef FS::directory_entry DirEntiry;

#define readNextLine std::getline

// integers

typedef unsigned int uint;
typedef size_t ulong;

// windows exceptions

#include <windows.h>
#include <excpt.h>

#define ExceptionInfo _EXCEPTION_POINTERS

// misc

#define setConsoleSync(x) \
    std::ios::sync_with_stdio(x)

#define UnderlyingType std::underlying_type