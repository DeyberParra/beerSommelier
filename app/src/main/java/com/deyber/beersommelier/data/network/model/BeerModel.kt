package com.deyber.beersommelier.data.network.model

import com.google.gson.annotations.SerializedName

data class BeerModel(

    @SerializedName("id"                ) var id               : Int?              = null,
    @SerializedName("name"              ) var name             : String?           = null,
    @SerializedName("tagline"           ) var tagline          : String?           = null,
    @SerializedName("first_brewed"      ) var firstBrewed      : String?           = null,
    @SerializedName("description"       ) var description      : String?           = null,
    @SerializedName("image_url"         ) var imageUrl         : String?           = null,
    @SerializedName("abv"               ) var abv              : Double?           = null,
    @SerializedName("ibu"               ) var ibu              : Double?              = null,
    @SerializedName("target_fg"         ) var targetFg         : Int?              = null,
    @SerializedName("target_og"         ) var targetOg         : Double?              = null,
    @SerializedName("ebc"               ) var ebc              : Int?              = null,
    @SerializedName("srm"               ) var srm              : Double?              = null,
    @SerializedName("ph"                ) var ph               : Double?           = null,
    @SerializedName("attenuation_level" ) var attenuationLevel : Double?              = null,
    @SerializedName("volume"            ) var volume           : Volume?           = Volume(),
    @SerializedName("boil_volume"       ) var boilVolume       : BoilVolume?       = BoilVolume(),
    @SerializedName("method"            ) var method           : Method?           = Method(),
    @SerializedName("ingredients"       ) var ingredients      : Ingredients?      = Ingredients(),
    @SerializedName("food_pairing"      ) var foodPairing      : ArrayList<String> = arrayListOf(),
    @SerializedName("brewers_tips"      ) var brewersTips      : String?           = null,
    @SerializedName("contributed_by"    ) var contributedBy    : String?           = null

)


data class Ingredients (

    @SerializedName("malt"  ) var malt  : ArrayList<Malt> = arrayListOf(),
    @SerializedName("hops"  ) var hops  : ArrayList<Hops> = arrayListOf(),
    @SerializedName("yeast" ) var yeast : String?         = null
)

data class Amount (

    @SerializedName("value" ) var value : Double?    = null,
    @SerializedName("unit"  ) var unit  : String? = null

)

data class Hops (

    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("amount"    ) var amount    : Amount? = Amount(),
    @SerializedName("add"       ) var add       : String? = null,
    @SerializedName("attribute" ) var attribute : String? = null

)

data class Malt (

    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("amount" ) var amount : Amount? = Amount()

)


data class Method (

    @SerializedName("mash_temp"    ) var mashTemp     : ArrayList<MashTemp> = arrayListOf(),
    @SerializedName("fermentation" ) var fermentation : Fermentation?       = Fermentation(),
    @SerializedName("twist"        ) var twist        : String?             = null

)

data class Fermentation (

    @SerializedName("temp" ) var temp : Temp? = Temp()

)
data class Temp (

    @SerializedName("value" ) var value : Int?    = null,
    @SerializedName("unit"  ) var unit  : String? = null

)

data class MashTemp (

    @SerializedName("temp"     ) var temp     : Temp? = Temp(),
    @SerializedName("duration" ) var duration : Int?  = null

)

data class Volume (

    @SerializedName("value" ) var value : Int?    = null,
    @SerializedName("unit"  ) var unit  : String? = null

)
data class BoilVolume (

    @SerializedName("value" ) var value : Int?    = null,
    @SerializedName("unit"  ) var unit  : String? = null

)


