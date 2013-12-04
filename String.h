/**
*Keenan Lee:kbl3236
*.h file for String.cpp
*/

#ifndef STRING_H
#define STRING_H

#include <iostream>
#include <stdexcept>

class String {

public: // ctors and dtor

    /** Name: String
     *
     * Description: Constructs a String of a certain size by
     *  dynamically allocating a valid C string.  The C string
     *  should be size + 1 in order to account for the null
     *  character pad.
     *
     *      String s1; 
     *
     * @param   size    the size of the string
     */
    String(int size = 0);

    /** Name: String
     *
     * Description: Constructs a String from a native C string.
     *
     *      String s1("hello");     // s1 = "hello";
     *
     * @param   s       the native c string
     */
    String(const char *s);

    /** Name: String
     *
     * Description: Copy constructs a String from another String.
     *  The C string should be dynamically allocated and copied
     *  from other's C String.
     *
     * @param   s       the native C string
     */
    String(const String& other);

    /** Name: String
     *
     * Description: Destructs a string object and delete's any
     *  dynamic memory associated with the C string.
     */
    ~String();

public: // common string routines

    /** Name: length
     *
     * Description: Get the length of the string.  This routine
     * ignores the null pad character when counting the size of 
     * the internal C string.
     *
     * @return the size of the string
     */
    size_t length() const;

    /** Name: c_str
     *
     * Description: Get the native C string within this String.
     *
     * @return the native C string
     */
    const char* c_str() const;

    /** Name: substring
     *
     * Description: Returns a new String which is a substring of 
     *  the original string.  
     *
     *  String s1("helloworld");
     *  s1.substring(3, 8);     // "lowor"
     *
     * @param   start   the starting index of the substring (inclusive)
     * @param   end     the ending index of the substring (exclusive)
     *
     * @return the substring from start to end-1 indexes
     *
     * @exception a range_error exception is thrown if the ranges
     *  are invalid.
     */
    String substring(int start, int end) const throw(std::range_error);

    /** Name: find
     *
     * Description: Returns the starting index of the other String,
     *  if it is present.  If the other String is not present, it
     *  returns -1.
     *
     *  String s1("helloworldhello");
     *  s1.find("world");       // 5
     *
     * @param   other   the String to find
     *
     * @return  the starting index of the other String, if present,
     *  otherwise -1.
     *
     */
    int find(String other) const;
    
public: // assignment methods

    /** Name: operator[]
     *
     * Description: Returns the character at the specified index
     *  in the String.  If the index is out of range, an
     *  exception is thrown.  This routine can be used
     *  for accessing as well as mutating a character
     *  in the string.
     *
     * @param   index   the index of the character in the String
     *
     * @return the character at the position in the String
     *
     * @exception a range_error exception is thrown if the 
     *  index is invalid
     */
    char& operator[](int index) const throw(std::range_error);

    /** Name: operator=
     *
     * Description: Assigns this String to another String object.
     *
     * @param   other   the other String object to be assigned from.
     *
     * @return a reference to this string object (to support
     *  chained assignment.
     */
    String& operator=(const String &other);

public: // comparison methods

    /** Name: operator==
     *
     * Description: Indicates whether this String is equivalent to
     *  another String object.
     *
     *      String s1("hello");
     *      String s2("world");
     *      s1 == s2;                // false
     *
     * @param   other   the other String object to be compared to.
     *
     * @return 'true' if the String's are equal, and 'false' otherwise.
     */
    bool operator==(const String &other) const;

    /** Name: operator<
     *
     * Description: Indicates whether this String is less than
     *  another String object.
     *      
     *      String s1("hello");
     *      String s2("world");
     *      s1 < s2;                // true
     *
     * @param   other   the other String object to be compared to.
     *
     * @return 'true' if this String is less than the other String,
     *  and 'false' otherwise.
     */
    bool operator<(const String &other) const;

    /**
     * Name: operator>
     *
     * Description: Indicates whether this String is greater than
     *  another String object.
     *      
     *      String s1("hello");
     *      String s2("world");
     *      s1 > s2;                // false
     *
     * @param   other   the other String object to be compared to.
     *
     * @return 'true' if this String is greater than the other String,
     *  and 'false' otherwise.
     */
    bool operator>(const String &other) const;

public: // conctenation and repetition methods

    /** Name: operator+
     * 
     * Description: Concatenates this String with another String.
     *
     *      String s1("hello");
     *      String s2("world");
     *      s1 + s2;    // "helloworld"
     *
     * @param   other   the other String to be concatenated with.
     *
     * @return A new String containing the concatenation.
     */
    String operator+(const String& other) const;

    /** Name: operator*
     *
     * Description: Creates a repetition of this String a certain number
     * of times.
     *
     *      String s1("hello");
     *      s1 * 3;    // "hellohellohello"
     *
     * @param   num     the number of repetitions of this string to make.
     *
     * @return  A new string with the repetition.
     */
    String operator*(int num) const;

    /** Name: operator*
     *
     * Description: Creates a repetition of this String a certain number
     * of times.
     *
     *      String s1("hello");
     *      3 * s1;    // "hellohellohello"
     *
     * @param   num     the number of repetitions of this string to make.
     *
     * @return  A new string with the repetition.
     */
    friend String operator*(int num, String& other);

public: // combo perform/assign methods

    /** Name: operator+=
     *
     * Description: Concatenates this String with another String, and
     *  assigns the result to this String.
     *
     *      String s1("hello");
     *      String s2("world");
     *      s1 += s2;           // s1="helloworld", s2="world"
     *
     * @param   other   the other String object to be concatenated with.
     *
     * @return A reference to this String.
     */
    String& operator+=(const String &other);

    /** Name: operator*=
     *
     * Description: Creates a repetition of this String a certain number
     * of times, and assigns that result to this String.
     *
     *      String s1("hello");
     *      s1 *= 3;    // s1 = "hellohellohello"
     *
     * @param   num     the number of repetitions of this string to make.
     *
     * @return A reference to this String.
     */
    String& operator*=(int num);

public:  // I/O methods

    /** Name: operator>>
     *
     * Description: Reads a string from the input stream into
     *  a String object.  Any pre-existing string is removed
     *  and a new one is created.
     *
     *      String s1;
     *      cin >> s1;    // input="hello", s1="hello
     *
     * @param   is  the input stream
     * @param   s   the String object to be read into
     *
     * @return  A reference to the input stream
     */
    friend std::istream& operator>>(std::istream &is, String &s);

    /** Name: operator<<
     *
     * Description: Print a String object to the output stream
     *
     *      String s1("hello");
     *      cout << s1;    // prints to standard output: hello
     *
     * @param   os  the output stream
     * @param   s   the String object to print
     *
     * @return  A reference to the output stream
     */
    friend std::ostream& operator<<(std::ostream &os, const String &s);

private:
    char *str;                          // the dynmically allocated C string
    int len;   // the maximum number of valid characters in the string
    static const int BUFF_INC = 20;     // the buffer increment value
    static const char* WHITESPACE;      // a string of whitespace characters
};

#endif
