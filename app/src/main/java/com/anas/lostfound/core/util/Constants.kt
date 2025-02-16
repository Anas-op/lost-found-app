package com.anas.lostfound.core.util


object UseCasesStrings {
    const val EMPTY_INPUT = "Empty input fields or no image uploaded"
}


object AuthStrings {
    const val EMPTY_ERROR = "Email or password can't be empty"
    const val EMAIL = "Email"
    const val PASSWORD = "Password"
    const val LOGIN = "Login"
    const val SIGNUP = "Signup"
    const val SIGNUP_BANNER = "Already have an account, Login"
    const val LOGIN_BANNER = "Don't have an account, Signup"

}


object NewUpdateStrings {
    const val CONFIRM_DELETE = "Are you sure you want to delete the item?"
    const val YES = "Yes"
    const val SAVE_ERROR = "An error occurred while saving the item"
}

object MapStrings {
    const val CURRENT_LOCATION = "Your Location"
    const val CURRENT_SNIPPET = "Current position"
    const val SELECTED_LOCATION = "Selected Location"
    const val SELECTED_SNIPPET = "Chosen Position"
}


object TabStrings {
    const val LOST = "Lost"
    const val FOUND = "Found"
}

object ListStrings {
    const val CANT_GET_ITEMS = "An error occurred while retrieving the items"
}

object ButtonStrings {
    const val GALLERY_UPLOAD = "Upload from gallery"
    const val LOGIN = "Login"
    const val SIGN_UP = "Create Account"
    const val CALL = "Call"
}

object InputFormStrings{
    const val TITLE = "Title:"
    const val DESCRIPTION = "Description:"
    const val CONTACT = "Contact:"
    const val EMAIL = "Email:"
    const val LOCATION = "Location:"
    const val CATEGORY = "Category:"
    const val SEARCH_LOCATION = "Search Location..."
    const val SEARCH_ITEM = "Search..."
}

object CardStrings{
    const val CATEGORY = "Category:"
    const val LOCATION = "Location:"
    const val FOUND_DATE = "Date Found:"
    const val LOST_DATE = "Date Lost:"
}

object ContentDescriptions {
    const val SAVE_ITEM = "Save"
    const val ADD_ITEM = "Add"
    const val DELETE = "Delete"
    const val GALLERY_SELECT = "Gallery Select"
    const val SEARCH = "Search Icon"
    const val CLOSE_SEARCH = "Close Icon"
    const val HISTORY_ICON = "History Icon"
    const val PHOTO_ITEM = "Photo Item"
    const val LOADING_INDICATOR = "Loading"
    const val SORTING_MENU = "Sorting Menu"
    const val CALL = "Call number"
    const val PASSWORD = "Toggle password visibility"
}


object CategoryConstants {
    val CATEGORIES = listOf(
        "All", "Electronics", "Health", "Education", "Travel",
        "Clothing & Accessories", "Pets & Animals", "Documents & IDs",
        "Keys & Wallets", "Jewelry & Watches", "Toys & Games",
        "Sports & Outdoor", "Tools & Equipment", "Musical Instruments",
        "Art & Collectibles"
    )
}

const val API_KEY = ""