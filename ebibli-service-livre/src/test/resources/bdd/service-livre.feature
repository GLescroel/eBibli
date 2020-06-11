#Cette feature traite les fonctionnalités du micro-service utilisateur
Feature: §Microservice - Gérer les livres
  En tant client du microservice livre,
  Je veux pouvoir rechercher les livres et modifier leur état
  Afin de pouvoir gérer les opérations sur les livres.

  Scenario Outline: recherche de tous les livres
    Given je recherche la liste de tous les livres
    When je demande cette liste au microservice Livre
    Then Les infos <rechercheAllLivresResultat> de la liste des livres du fichier json sont retournées
    Examples:
    Examples:
      | rechercheAllLivresResultat  |
      | rechercheAllLivresResultat  |

  Scenario Outline: recherche de tous les livres disponibles
    Given je recherche la liste de tous les livres disponibles
    When je demande cette liste au microservice Livre
    Then Les infos <rechercheAllLivresDispoResultat> de la liste des livres du fichier json sont retournées
    Examples:
    Examples:
      | rechercheAllLivresDispoResultat  |
      | rechercheAllLivresDispoResultat  |

  Scenario Outline: recherche de tous les livres d'une bibliotheque
    Given je recherche la liste de tous les livres de la bibliotheque "3"
    When je demande cette liste au microservice Livre
    Then Les infos <rechercheAllLivresByBibliothequeResultat> de la liste des livres du fichier json sont retournées
    Examples:
    Examples:
      | rechercheAllLivresByBibliothequeResultat  |
      | rechercheAllLivresByBibliothequeResultat  |

  Scenario Outline: recherche de tous les livres d'un ouvrage
    Given je recherche la liste de tous les livres de l'ouvrage "15"
    When je demande cette liste au microservice Livre
    Then Les infos <rechercheAllLivresByOuvrageResultat> de la liste des livres du fichier json sont retournées
    Examples:
    Examples:
      | rechercheAllLivresByOuvrageResultat  |
      | rechercheAllLivresByOuvrageResultat  |

  Scenario Outline: recherche de tous les livres dispo d'un ouvrage
    Given je recherche la liste de tous les livres disponibles de l'ouvrage "15"
    When je demande cette liste au microservice Livre
    Then Les infos <rechercheAllLivresDispoByOuvrageResultat> de la liste des livres du fichier json sont retournées
    Examples:
    Examples:
      | rechercheAllLivresDispoByOuvrageResultat  |
      | rechercheAllLivresDispoByOuvrageResultat  |

  Scenario Outline: La vie d'un livre de bibliothèque
    Given le livre d'id "<livreId>" est disponible dans la bibliothèque "<bibliothequeId>"
    When l'abonné d'id "<abonneId>" réserve ce livre
    Then le livre "<livreId>" est réservé pour l'abonné d'id "<abonneId>"
    When l'abonné d'id "<abonneId>" emprunte le livre "<livreId>"
    Then La réservation est supprimée
    And le livre "<livreId>" est emprunté par l'abonné d'id "<abonneId>"
    And le livre "<livreId>" n'est plus réservé
    When l'abonné d'id "<abonneId>" retourne le livre "<livreId>"
    Then Le livre d'id "<abonneId>" est disponible
    Examples:
      | livreId  | bibliothequeId | abonneId |
      | 22       | 3              | 4        |
