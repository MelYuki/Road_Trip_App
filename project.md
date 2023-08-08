# Réaliser une application de Road-trip
- Permettre à l'utilisateur de chercher des villes
    [https://nominatim.org/release-docs/develop/api/Search/]
- Permettre à l'utilisateur d'obtenir des informations sur les villes : 
    * Afficher le lieu sur la carte
    * Points d'intérêts [https://docs.mapbox.com/api/search/geocoding/]
    * La météo actuelle [https:/openweathermaporg/current]
    * La distance approximative (à vol d'oiseau) avec la position actuelle
- Permettre à l'utilisateur de créer un road-trip [-> Save in DB]
    * Sélectionner des lieux (villes)
    * Définir les dates 
    * Afficher sur la carte (Via un fragment G-Maps) le road-trip
- Permettre à l'utilisateur de mettre en favoris une ville

## Contraintes : 
- Application en kotlin avec Android (Surprise /ᐠ｡ꞈ｡ᐟ\ )
- Exploiter les fragments
- Créer une interface utilisateur « propre »
- Personnaliser l'icône de l'app
- Rendre l'application utilisable en Français et Anglais (Plus, si vous êtes chaud)
- Adapter le visuel pour le light et dark thème

### NB : D'autres contraintes et demandes peuvent être ajoutées par la suite par le client (le formateur)