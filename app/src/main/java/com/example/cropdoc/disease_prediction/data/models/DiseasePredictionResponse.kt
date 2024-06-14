package com.example.cropdoc.disease_prediction.data.models

data class DiseasePredictionResponse(
    val prediction: String
)

fun getDiseaseSolutions(diseaseName: String): String {
    return when (diseaseName) {
        "apple apple scab" -> {
            "General Prevention: Choose scab-resistant varieties and ensure proper sanitation by removing fallen leaves and fruit. Prune trees to improve air circulation.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing captan, mancozeb, or sulfur during the growing season. Regular preventive sprays on emerging leaves and fruit are essential to manage apple scab."
        }

        "apple black rot" -> {
            "General Prevention: Remove any infected branches or leaves to prevent the spread of the disease. Sanitation is crucial; make sure to dispose of infected plant material properly.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing captan or thiophanate-methyl according to the manufacturer's instructions. These treatments should be started in the spring and continued through the growing season to protect new growth and fruit from infection."
        }

        "apple cedar apple rust" -> {
            "General Prevention: Prune infected branches during dry weather to minimize the spread of spores. Dispose of infected plant material properly to avoid reinfection.\n\n" +
                    "Fungicide Treatment: Use fungicides containing myclobutanil or triadimefon. Apply at the pink bud stage and repeat at 7-10 day intervals as per label directions to effectively manage the disease."
        }

        "apple healthy" -> {
            "Maintain regular watering and ensure proper care to keep the apple tree healthy."
        }

        "blueberry healthy" -> {
            "Water daily and ensure proper sunlight for optimal growth."
        }

        "cherry including sour powdery mildew" -> {
            "General Prevention: Improve air circulation around the plants by proper pruning and spacing. Avoid overhead watering to reduce humidity levels around the foliage.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing sulfur, neem oil, or potassium bicarbonate at the first signs of the disease. Repeat applications as recommended to protect new growth."
        }

        "cherry including sour healthy" -> {
            "Ensure regular watering and proper care to maintain tree health."
        }

        "corn maize cercospora leaf spot gray leaf spot" -> {
            "General Prevention: Rotate crops with non-host species and plant resistant varieties to reduce the incidence of the disease. Avoid overhead irrigation to minimize leaf wetness.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing strobilurins or triazoles early in the growing season. Follow the label directions for application timing and frequency to protect the crop throughout the season."
        }

        "corn maize common rust" -> {
            "General Prevention: Plant resistant varieties and ensure good field sanitation by removing crop residues. Rotate crops to prevent the buildup of rust spores in the soil.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing chlorothalonil or mancozeb at the first signs of rust. Regular applications may be necessary to keep the disease under control during periods of high humidity."
        }

        "corn maize northern leaf blight" -> {
            "General Prevention: Use resistant hybrids and practice crop rotation to prevent the buildup of the pathogen. Maintain proper field sanitation by removing infected plant debris.\n\n" +
                    "Fungicide Treatment: Apply fungicides with active ingredients like azoxystrobin or propiconazole when symptoms first appear. Follow the recommended schedule for repeat applications to protect the crop throughout the growing season."
        }

        "corn maize healthy" -> {
            "Regular watering and proper fertilization are key to maintaining healthy corn."
        }

        "grape black rot" -> {
            "General Prevention: Remove and destroy diseased plant parts and fallen leaves. Ensure good air circulation through proper pruning and spacing.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing myclobutanil or captan starting in the early spring. Continue applications at 7-10 day intervals, especially during wet weather, to protect new growth and fruit."
        }

        "grape esca black measles" -> {
            "General Prevention: Prune infected wood and practice good vineyard management to reduce vine stress. Avoid overhead irrigation to minimize leaf wetness.\n\n" +
                    "Fungicide Treatment: Apply fungicides if recommended by local guidelines. Maintain overall vine health through proper fertilization and irrigation practices to reduce susceptibility to the disease."
        }

        "grape leaf blight isariopsis leaf spot" -> {
            "General Prevention: Remove infected leaves and improve air circulation through proper pruning and training of vines. Avoid overhead watering.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing copper or mancozeb at the first signs of the disease. Repeat applications as needed to protect new growth, especially during wet weather."
        }

        "grape healthy" -> {
            "Maintain regular watering and proper vineyard care to ensure healthy grapevines."
        }

        "orange haunglongbing citrus greening" -> {
            "General Prevention: Monitor for and control the Asian citrus psyllid, the vector of the disease, using approved insecticides. Remove and destroy infected trees to prevent the spread of the disease.\n\n" +
                    "Management Practices: Use insecticide treatments and biological controls to manage psyllid populations. Ensure good nutrition and care for trees to help them tolerate the disease."
        }

        "peach bacterial spot" -> {
            "General Prevention: Prune affected areas and ensure good air circulation by proper spacing and pruning. Avoid overhead irrigation.\n\n" +
                    "Fungicide Treatment: Apply copper-based bactericides early in the season and continue as needed based on weather conditions and disease pressure. Follow label directions for proper application."
        }

        "peach healthy" -> {
            "Water daily and ensure proper fertilization and care to keep the peach tree healthy."
        }

        "pepper bell bacterial spot" -> {
            "General Prevention: Practice crop rotation and use disease-free seeds and transplants. Avoid overhead watering and ensure proper spacing for air circulation.\n\n" +
                    "Fungicide Treatment: Apply copper-based bactericides at the first signs of infection. Repeat applications as necessary to protect new growth, especially during wet or humid weather."
        }

        "pepper bell healthy" -> {
            "Regular watering and proper sunlight are essential for healthy pepper plants."
        }

        "potato early blight" -> {
            "General Prevention: Use certified disease-free seed potatoes and practice crop rotation. Remove and destroy infected plant debris at the end of the season.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing chlorothalonil or mancozeb starting at the first signs of the disease. Repeat applications according to label directions to protect new growth."
        }

        "potato late blight" -> {
            "General Prevention: Use certified disease-free seed potatoes and ensure good field sanitation by removing infected plant debris. Avoid overhead irrigation.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing mancozeb or metalaxyl at the first signs of the disease. Follow recommended application schedules to protect the crop throughout the growing season."
        }

        "potato healthy" -> {
            "Ensure regular watering and proper fertilization to maintain healthy potato plants."
        }

        "raspberry healthy" -> {
            "Water daily and provide proper care for optimal growth."
        }

        "soybean healthy" -> {
            "Regular watering and proper soil management are essential for healthy soybean plants."
        }

        "squash powdery mildew" -> {
            "General Prevention: Improve air circulation around the plants by proper pruning and spacing. Avoid overhead watering to reduce humidity levels around the foliage.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing sulfur, neem oil, or potassium bicarbonate at the first signs of the disease. Repeat applications as recommended to protect new growth."
        }

        "strawberry leaf scorch" -> {
            "General Prevention: Remove infected leaves and improve air circulation around the plants. Avoid overhead watering to reduce leaf wetness.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing myclobutanil or captan at the first signs of the disease. Continue applications as needed to protect new growth, especially during wet weather."
        }

        "strawberry healthy" -> {
            "Ensure regular watering and proper care to keep strawberry plants healthy."
        }

        "tomato bacterial spot" -> {
            "General Prevention: Use disease-free seeds and transplants, and practice crop rotation. Avoid working with plants when they are wet to minimize the spread of bacteria.\n\n" +
                    "Fungicide Treatment: Apply copper-based bactericides at the first signs of infection. Repeat applications as necessary to protect new growth, especially during wet weather."
        }

        "tomato early blight" -> {
            "General Prevention: Use disease-free seeds and practice crop rotation. Remove and destroy infected plant debris to reduce the source of infection.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing chlorothalonil or mancozeb at the first signs of the disease. Repeat applications according to label directions to protect new growth."
        }

        "tomato late blight" -> {
            "General Prevention: Use certified disease-free seeds and practice crop rotation. Remove and destroy infected plant debris to reduce the source of infection.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing mancozeb or metalaxyl at the first signs of the disease. Follow recommended application schedules to protect the crop throughout the growing season."
        }

        "tomato leaf mold" -> {
            "General Prevention: Ensure good air circulation around plants by proper spacing and pruning. Avoid overhead watering to reduce humidity. Remove and dispose of infected plant debris promptly.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing chlorothalonil or copper-based fungicides early in the season or preventively if conditions favor disease development."
        }

        "tomato septoria leaf spot" -> {
            "General Prevention: Use crop rotation and avoid overhead watering. Remove and destroy infected leaves. Ensure good air circulation around plants.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing chlorothalonil or copper fungicides preventively or at the first sign of disease. Follow label instructions carefully."
        }

        "tomato spider mites two spotted spider mite" -> {
            "General Prevention: Monitor plants regularly for early signs of infestation. Use reflective mulch to deter mites. Introduce predatory mites if feasible.\n\n" +
                    "Miticide Treatment: Apply miticides such as sulfur or insecticidal soap when mite populations are low to moderate. Repeat applications as needed, following product instructions."
        }

        "tomato target spot" -> {
            "General Prevention: Practice crop rotation and maintain good garden hygiene by removing plant debris. Avoid overhead watering to reduce humidity.\n\n" +
                    "Fungicide Treatment: Apply fungicides containing chlorothalonil or copper fungicides preventively, especially during periods of high humidity or rainfall."
        }

        "tomato tomato yellow leaf curl virus" -> {
            "General Prevention: Plant virus-resistant tomato varieties if available. Control whiteflies, which transmit the virus, using insecticides or sticky traps.\n\n" +
                    "Management: There is no cure for the virus. Promptly remove and destroy infected plants to prevent spread. Manage weeds that can host whiteflies."
        }

        "tomato tomato mosaic virus" -> {
            "General Prevention: Plant resistant tomato varieties. Control aphids, which spread the virus, using insecticides or reflective mulch.\n\n" +
                    "Management: There is no cure for the virus. Remove and destroy infected plants immediately to prevent spread. Practice strict hygiene to avoid transmission."
        }

        "tomato healthy" -> {
            "General Care: Ensure plants receive adequate sunlight, water, and nutrients according to their specific needs. Monitor for pests and diseases regularly.\n\n" +
                    "Preventive Measures: Maintain good garden hygiene by removing debris. Support plants with stakes or cages as needed."
        }

        "Healthy Leaf" -> {
            "Water daily and provide proper care for optimal growth."
        }

        else -> "Disease information not found."
    }
}
