package com.example.grocerygo.extras

object Endpoints {
    // public
    const val HOME_IMAGE = Config.ENDPOINT_HOME_IMAGE

    // private
    private const val URL_SUB_DIR_CATEGORY = "category"
    private const val URL_SUB_DIR_SUBCATEGORY = "subcategory"
    private const val URL_SUB_DIR_PRODUCTS = "products"
    private const val URL_END_LOGIN = "auth/login"
    private const val URL_END_REGISTER = "auth/register"
    private const val URL_END_POST_ADDRESS = "address"
    private const val URL_END_GET_ADDRESSES = "address/"

    // derivative
    val categories: String
        get() = Config.BASE_URL + URL_SUB_DIR_CATEGORY
    val login: String
        get() = Config.BASE_URL + URL_END_LOGIN
    val register: String
        get() = Config.BASE_URL + URL_END_REGISTER

    fun getSelectedProductsEndpoint(subCatIndex: Int): String {
        return Config.BASE_URL + URL_SUB_DIR_PRODUCTS + "/sub/$subCatIndex"
    }

    fun getSelectedSubCategoriesEndpoint(catIndex: Int): String {
        return Config.BASE_URL + URL_SUB_DIR_SUBCATEGORY + "/$catIndex"
    }

    fun getImageEndpoint(imageName:String):String {
        return Config.BASE_URL_ITEM_IMAGES + imageName
    }

    fun getPostAddressEndpoint():String {
        return Config.BASE_URL + URL_END_POST_ADDRESS
    }

    fun getAddressesEndpoint(userID:String):String {
        return Config.BASE_URL + URL_END_GET_ADDRESSES + userID
    }
}

