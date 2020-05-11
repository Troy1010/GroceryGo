package com.example.grocerygo_jsonparsingandconfig.extras

class Endpoints {
    companion object {
        const val CATEGORY = "category"
        const val SUBCATEGORY = "subcategory"
        const val PRODUCTS = "products"

        var vCategoryEndpoint: String
            get() = Config.BASE_URL + CATEGORY
            set(value) {}

        fun getSelectedProductsEndpoint(subCatIndex: Int): String {
            return Config.BASE_URL + PRODUCTS + "/sub/$subCatIndex"
        }

        fun getSelectedSubCategoriesEndpoint(catIndex: Int): String {
            return Config.BASE_URL + SUBCATEGORY + "/$catIndex"
        }
    }

}