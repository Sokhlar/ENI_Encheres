CREATE   PROCEDURE actualizeAuctionsState
AS
DECLARE @date_debut date
DECLARE date_cursor CURSOR FOR
    SELECT date_debut_encheres
    FROM ARTICLES_VENDUS
    WHERE etat_vente = 'PC';

    OPEN date_cursor
    FETCH NEXT FROM date_cursor
    INTO @date_debut

    WHILE @@FETCH_STATUS = 0
        BEGIN
            IF @date_debut <= GETDATE()
                BEGIN
                    UPDATE ARTICLES_VENDUS SET etat_vente = 'EC' WHERE date_debut_encheres = @date_debut
                end
            FETCH NEXT FROM date_cursor
            INTO @date_debut
        end
    CLOSE date_cursor
DEALLOCATE date_cursor
go

