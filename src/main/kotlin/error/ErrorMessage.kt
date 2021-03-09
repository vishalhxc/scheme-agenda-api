package error

enum class ErrorMessage(val text: String) {
    BadRequest("Bad request: there are errors in the request."),
    NotFound("Not found: resource not found."),
    Conflict("Conflict: resource already exists."),
    InternalServerError("Internal server error: there was an error processing the request."),
    Empty("");
    override fun toString(): String = this.text
    companion object {
        fun fromText(text: String): ErrorMessage =
            values().find { it.text == text } ?: Empty
    }
}