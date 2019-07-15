namespace java com.itmuch.thrift
namespace py   py.thrift
typedef i16 short
typedef i32 int
typedef i64 long
typedef string String
typedef bool boolean

struct Person{
    1: optional String username,
    2: optional int age,
    3: optional boolean married
}

exception DateException{
    1: optional String message,
    2: optional String callStack,
    3: optional String date
}

service PersonService{
    Person getPersionByUsername(1:required String username) throws (1: DateException dateEx),

    void savePerson(1:required Person Person) throws (1: DateException dateEx)
}