#Cette feature traite les fonctionnalités du micro-service emprunt
Feature: §Microservice - Gérer les emprunts
  En tant client du microservice emprunt,
  Je veux pouvoir créer, rechercher, prolonger, clôre les emprunts
  Afin de pouvoir gérer les emprunts des abonnés.

  Scenario Outline: cycle de vie normal d'un prêt
    Given l'abonné d'"<utilisateur_id>" existe et le livre d'"<livre_id>" est disponible
    When l'abonné d'"<utilisateur_id>" emprunte le livre d'"<livre_id>"
    And on recherche les emprunts en cours de l'abonné
    And on recherche l'emprunt en cours pour ce livre
    Then l'emprunt fait bien parti des emprunts en cours de l'abonné
    And l'emprunt est bien en cours pour ce livre
    When l'abonné d'"<utilisateur_id>" prolonge l'emprunt du livre d'"<livre_id>"
    Then l'emprunt a été prolongé
    When l'abonné d'"<utilisateur_id>" retourne le livre d'"<livre_id>"
    Then l'emprunt est terminé
    And l'emprunt de l'abonné "<utilisateur_id>" est dans sa liste d'emprunts terminés
    And l'emprunt de l'abonné "<utilisateur_id>" est dans sa liste complète d'emprunts
    When l'abonné d'"<utilisateur_id>" est supprimé de l'emprunt
    Then l'abonné d'"<utilisateur_id>" n'est plus référencé dans l'emprunt
    Examples:
      | utilisateur_id  | livre_id |
      | 4               | 23       |
