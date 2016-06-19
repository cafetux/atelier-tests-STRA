# Pharma-shopping
Pharma shopping est un exercice de refactoring de code legacy.

Les points d'entrée sont dans le package *com.pharmashopping.vente_de_produits.action*
Il s'agit des classes
- RetrieveProduitsToSellAction

Ce service permet de voir les problématiques de couplage fortes, et de classes non testable en l'état.

- EnregistrerUneVenteAction

Cette classe permet de travailler le clean code, avec une méthode ne respectant pas SRP.
