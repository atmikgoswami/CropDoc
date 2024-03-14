data class CropSuggestionResponse(
    val prediction: String
)

data class CropData(
    val temp: Float,
    val humidity: Float,
    val ph: Float,
    val water: Float,
    val season: Int
)
