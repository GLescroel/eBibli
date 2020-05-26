#Cette feature traite les fonctionnalités du micro-service utilisateur
Feature: §Microservice - Gérer les utilisateurs
  En tant client du microservice utilisateur,
  Je veux pouvoir créer, rechercher, supprimer les utilisateurs
  Afin de pouvoir gérer les utilisateurs.

  Scenario Outline: recherche d'un utilisateur par son email
    Given je recherche l'utilisateur par son "<email>"
    When j'interroge le microservice utilisateur
    Then Les infos <rechercheUtilisateurResultat> de l'utilisateur du fichier rechercheUtilisateurResultat.json sont retournées
    Examples:
      | email  | rechercheUtilisateurResultat  |
      | glescroel@hotmail.com | rechercheUtilisateurResultat1 |
      | gregory.lescroel@axa.fr | rechercheUtilisateurResultat2 |
      | admin@oc.com | rechercheUtilisateurResultat3 |
      | smith@oc.com | rechercheUtilisateurResultat4 |
