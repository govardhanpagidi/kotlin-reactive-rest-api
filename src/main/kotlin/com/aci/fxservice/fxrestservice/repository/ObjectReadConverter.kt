//package com.aci.fxservice.fxrestservice.repository
//
//import com.aci.fxservice.fxrestservice.entity.FxRateKey
//import org.bson.Document
//import org.bson.types.ObjectId
//import org.springframework.core.convert.converter.Converter
//import org.springframework.data.convert.ReadingConverter
//
//@ReadingConverter
//class ObjectIdToFxRateKeyConverter : Converter<ObjectId, FxRateKey> {
//    override fun convert(source: ObjectId): FxRateKey {
//        // Implement the conversion logic from ObjectId to FxRateKey here
//        // Return the FxRateKey object created from the ObjectId fields
//        val result = source.toString().split(",")
//        return FxRateKey(result[0].toInt(), result[1].toInt(), result[2], result[3])
//    }
//}
