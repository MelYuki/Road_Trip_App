# Réaliser une application Android de Road-trip
- Permettre à l'utilisateur de chercher des villes
Utiliser une des API suivantes pour réaliser la fonctionnalité :
    * https://nominatim.org/release-docs/develop/api/Search/
    * https://countrystatecity.in/docs/
- Permettre à l'utilisateur d'obtenir des informations sur les villes : 
    * Afficher le lieu sur la carte
    * Point d'intérêt
    * La météo actuelle
    * La distance approximative (à vol d'oiseau) avec la position actuelle
- Permettre à l'utilisateur de créer un road-trip [-> Save in DB]
    * Sélectionner des villes
    * Définir les dates 
    * Afficher sur la carte le road-trip
- Permettre à l'utilisateur de mettre en favori une ville

# Contraintes
- Application en kotlin avec Android (Surprise /ᐠ｡ꞈ｡ᐟ\ )
- Exploiter les fragments
- Créer une interface utilisateur « propre »
- Personnaliser l'icône de l'app
- Rendre l'application utilisable en Français et Anglais (Plus, si vous êtes chaud)
- Adapter le visuel pour le light et dark thème

# Informations
- Librairie
    * Carte interactive : « Maps SDK for Android » de mapbox
        * https://docs.mapbox.com/android/maps/guides/install/
    * Charger et afficher des images : « Glide »
        * https://bumptech.github.io/glide/
- API
    * Pour rechercher des villes :
        * https://nominatim.org/release-docs/develop/api/Search/
        * https://countrystatecity.in/docs/
    * Pour la météo : « Open Weather Map »
        * https:/openweathermap.org/current
    * Pour obtenir les points d'intérêt d'une ville : « Geacoding » de mapbox
        * https://docs.mapbox.com/api/search/geocoding/
    * Pour rechercher des images par mot clef : « photos-search » de pexels
        * https://www.pexels.com/fr-fr/api/documentation/?#photos-search

### <i>NB : D'autres contraintes et demandes peuvent être ajoutés par la suite par le client (le formateur)</i>