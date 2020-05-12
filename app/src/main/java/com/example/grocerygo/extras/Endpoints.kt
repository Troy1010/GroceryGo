package com.example.grocerygo.extras

object Endpoints {
    // public
    const val HOME_IMAGE = Config.ENDPOINT_HOME_IMAGE

    // private
    private const val URL_SUB_DIR_CATEGORY = "category"
    private const val URL_SUB_DIR_SUBCATEGORY = "subcategory"
    private const val URL_SUB_DIR_PRODUCTS = "products"

    // derivative
    val vCategoryEndpoint: String
        get() = Config.BASE_URL + URL_SUB_DIR_CATEGORY

    fun getSelectedProductsEndpoint(subCatIndex: Int): String {
        return Config.BASE_URL + URL_SUB_DIR_PRODUCTS + "/sub/$subCatIndex"
    }

    fun getSelectedSubCategoriesEndpoint(catIndex: Int): String {
        return Config.BASE_URL + URL_SUB_DIR_SUBCATEGORY + "/$catIndex"
    }
}

