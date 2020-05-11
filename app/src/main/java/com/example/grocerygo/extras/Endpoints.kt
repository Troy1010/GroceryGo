package com.example.grocerygo.extras

class Endpoints {
    companion object {
        private const val CATEGORY = "category"
        private const val SUBCATEGORY = "subcategory"
        private const val PRODUCTS = "products"

        val vCategoryEndpoint: String
            get() = Config.BASE_URL + CATEGORY

        fun getSelectedProductsEndpoint(subCatIndex: Int): String {
            return Config.BASE_URL + PRODUCTS + "/sub/$subCatIndex"
        }

        fun getSelectedSubCategoriesEndpoint(catIndex: Int): String {
            return Config.BASE_URL + SUBCATEGORY + "/$catIndex"
        }
    }

}