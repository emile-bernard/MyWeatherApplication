package com.example.myweatherapplication.city

class City(var id: Long, var name:String) {
    constructor(name:String): this(-1, name)
}