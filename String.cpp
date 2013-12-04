/**
*Keenan Lee:kbl3236
*C++ string class
*/

#include <iostream>
#include <iomanip>
#include <cstring>
#include <sstream>
#include <cstdio>
#include "String.h"

using namespace std;

const char* String::WHITESPACE = " \n\r\t";

// String ctor
String::String(int size) :
    str(new char [size + 1]),               // create the string
    len(size) {                             // store the length
    str[0] = '\0';			    // null terminate start
} 

// String ctor
String::String(const char *s) :
    str(new char [strlen(s) + 1]),          // create the string
    len(strlen(s)) {                        // store the length
    strncpy(str, s, len);                   // copy the string over
    str[len] = '\0';                        // null terminate it
    
}

// String copy ctor
String::String(const String& other) {
    str = new char [other.len + 1];         // create the string
    strncpy(str, other.str, other.len);     // copy over the contents
    str[other.len] = '\0';                  // null terminate it
    len = other.len;                        // store the length
}

// String destructor
String::~String() {
    delete [] str;                     
}

// length
size_t String::length() const {
    return len;
}

// c_str
const char* String::c_str() const {
    return str;
}

// substring
String String::substring(int start, int end) const throw(std::range_error) {
    // if the indexes are invalid, throw an exception
    if (start > end || start < 0 || end > len) {
        throw range_error("***Substring index out of range***");
    }

    // make a temporary string to hold the substring
    char *temp = new char [end-start+1];

    // copy over the substring and null terminate it
    strncpy(temp, str+start, end-start);
    temp[end-start] = '\0';

    // create a String object to hold the result
    String result(temp);

    // delete the temporary string
    delete [] temp;

    return result;
}

// find
int String::find(String other) const {
    int index = -1;     // if the string is not found return -1

    // get the starting address of the other string in this string
    char *ptr = strstr(str, other.str);

    if (ptr) {
        // subtract the two addresses to get the index
        index = (ptr-str)/(sizeof(char));
    }
    return index;
}

// operator[]
char& String::operator[](int index) const throw(range_error) {
   if(index < 0 || index > len)
      throw range_error("***Substring index out of range***");
   char& ch = str[index];
    
   return ch;
}

// operator=
String& String::operator=(const String& other) {
    // avoid self-assignment
    if (this != &other) {
        delete [] str;                          // delete existing space
        str = new char [other.len + 1];         // allocate new string
        strncpy(str, other.str, other.len);     // copy over the contents
        str[other.len] = '\0';                  // null pad the new string
        len = other.len;                        // store the length
    }

    return *this;                               
}

// operator==
bool String::operator==(const String &other) const {
    if(strcmp(this->str,other.str) == 0)
       return true;
    
    return false;	
}

// operator<
bool String::operator<(const String& other) const {
    if(strcmp(this->str,other.str) < 0)
       return true;
    return false;
}

// operator>
bool String::operator>(const String &other) const {
    if(strcmp(this->str,other.str) > 0)
        return true;
    return false;
}

// operator+
String String::operator+(const String& other) const {
    // allocate a new string to hold the concetenation
    char *tmp = new char [len + other.len + 1];

    strncpy(tmp, str, len);     // copy this string into tmp
    tmp[len] = '\0';
    strncat(tmp, other.str, other.len);  // concatenate other string
    tmp[len+other.len] = '\0';
    String result(tmp);         // create a String to hold the result
    delete [] tmp;              // delete the temporary string

    return result;
}

// operator*
String String::operator*(int num) const {
   String st;
   for(int i = 0; i < num; ++i)
      st += this->str;
   return st;
}

// operator*
String operator*(int num, String& other) {
    String st;
    for(int i = 0; i < num; ++i)
       st += other.str;
    
    return st;
}

// operator+=
String& String::operator+=(const String& other) {
    *this = *this + other;

    return *this;
}

// operator*=
String& String::operator*=(int num) {
    String st(*this);
    for(int i = 1; i < num; ++i)
       *this += st;
    return *this;
}

// operator>>
istream& operator>>(istream &is, String &s) {
   char chr;

   int used = 0;
   int len = s.BUFF_INC;

   if(s.str)
      delete[] s.str;

   s.str = new char[len];
   is >> ws;

   while(is.get(chr) && !is.fail() && !strrchr(s.WHITESPACE,chr)){
      if(used + 1 >= len){
         len = len + String::BUFF_INC;
	 char * temp = new char[len];
	 s.str[len] = '\0';
         strcpy(temp,s.str);

	 delete [] s.str;
	 s.str = temp;
      }
      s.str[used++] = chr;
   }
   s.str[used++] = '\0';

   s.len = strlen(s.str);
  
   if(strchr(s.WHITESPACE,chr))
       is.putback(chr);

   return is;
}

// operator<<
ostream& operator<<(ostream &os, const String &s) {
    os << s.str;
    return os;
}
