package ir.ehsan.telegram.free.utils

fun mergePhoneNumber(code:String,phoneNumber:String):String{
    var _phoneNumber = phoneNumber
    if (_phoneNumber.substring(0,1) == "0"){
        _phoneNumber = _phoneNumber.substring(1)
    }
    return "$code${_phoneNumber}"
}