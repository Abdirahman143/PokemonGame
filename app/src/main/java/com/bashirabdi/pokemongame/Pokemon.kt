package com.bashirabdi.pokemongame

import android.location.Location


class Pokemon{

    var name:String?=null
    var des:String?= null
    var image:Int?=0
    var pow:Double?=null
    var location:Location?=null
    var isCatch:Boolean?=false


    constructor(image:Int, name:String ,des:String, pow:Double,Lat:Double, Lon:Double)

    {
        this.name= name;
        this.des = des
        this.image= image
        this.location=Location(name)
        this.location!!.latitude=Lat
        this.location!!.longitude =Lon

        this.pow =pow
        this.isCatch=false

    }
}