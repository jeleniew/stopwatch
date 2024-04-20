package com.example.stop_watch_notify.model

class RingtoneModel : OptionModel {

    override var id: Int = -1
    var name: String = ""
    var location: String? = null
    var resId: Int? = null
    var default: Boolean = true

    constructor(id: Int, name: String, resId: Int) {
        this.id = id
        this.name = name
        this.resId = resId
        this.default = true
    }

    constructor(id: Int, name: String, location: String) {
        this.id = id
        this.name = name
        this.location = location
        this.default = false
    }

    fun changeName(name: String) {
        this.name = name
    }

}