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

fun getCropDetails(cropName: String): String {
    return when (cropName) {
        "blackgram" -> {
            "Soil: Well-drained loamy soil.\n\nClimate: Warm and humid; 25-30°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Kharif (June-July), Rabi (October-November).\n\nHarvest Time: 70-90 days after sowing."
        }
        "rice" -> {
            "Soil: Clayey loam or loam soil with good water retention.\n\nClimate: Warm and humid; 20-30°C.\n\nWater: Requires abundant water; standing water during growth.\n\nSowing Time: Kharif (June-July).\n\nHarvest Time: 120-150 days after sowing."
        }
        "chickpea" -> {
            "Soil: Well-drained sandy loam or clay loam.\n\nClimate: Cool and dry; 18-30°C.\n\nWater: Low to moderate; avoid waterlogging.\n\nSowing Time: Rabi (October-November).\n\nHarvest Time: 90-120 days after sowing."
        }
        "cotton" -> {
            "Soil: Well-drained loamy or clayey soil.\n\nClimate: Warm and dry; 21-30°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Kharif (April-May).\n\nHarvest Time: 150-180 days after sowing."
        }
        "jute" -> {
            "Soil: Well-drained loamy soil.\n\nClimate: Warm and humid; 24-37°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Kharif (March-May).\n\nHarvest Time: 120-150 days after sowing."
        }
        "kidneybeans" -> {
            "Soil: Well-drained loamy soil.\n\nClimate: Cool and dry; 15-25°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Kharif (June-July), Rabi (October-November).\n\nHarvest Time: 90-120 days after sowing."
        }
        "lentil" -> {
            "Soil: Well-drained loamy soil.\n\nClimate: Cool and dry; 18-30°C.\n\nWater: Low to moderate; avoid waterlogging.\n\nSowing Time: Rabi (October-November).\n\nHarvest Time: 100-110 days after sowing."
        }
        "maize" -> {
            "Soil: Well-drained loamy soil.\n\nClimate: Warm and moderately humid; 21-27°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Kharif (June-July), Rabi (October-November).\n\nHarvest Time: 90-120 days after sowing."
        }
        "mothbeans" -> {
            "Soil: Well-drained sandy loam or loamy soil.\n\nClimate: Warm and dry; 25-30°C.\n\nWater: Low; drought-resistant.\n\nSowing Time: Kharif (July-August).\n\nHarvest Time: 70-90 days after sowing."
        }
        "mungbean" -> {
            "Soil: Well-drained loamy soil.\n\nClimate: Warm and moderately humid; 25-35°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Kharif (June-July), Rabi (February-March).\n\nHarvest Time: 60-70 days after sowing."
        }
        "muskmelon" -> {
            "Soil: Well-drained sandy loam or loamy soil.\n\nClimate: Warm; 24-27°C.\n\nWater: Moderate; regular watering.\n\nSowing Time: Summer (February-March).\n\nHarvest Time: 75-100 days after sowing."
        }
        "pigeonpeas" -> {
            "Soil: Well-drained loamy or sandy loam soil.\n\nClimate: Warm; 18-30°C.\n\nWater: Moderate; drought-resistant.\n\nSowing Time: Kharif (June-July).\n\nHarvest Time: 150-180 days after sowing."
        }
        "watermelon" -> {
            "Soil: Well-drained sandy loam or loamy soil.\n\nClimate: Warm; 25-30°C.\n\nWater: Moderate; avoid waterlogging.\n\nSowing Time: Summer (January-March).\n\nHarvest Time: 80-90 days after sowing."
        }
        else -> {
            "No information available for the specified crop."
        }
    }
}

