package es.rufflecol.sam.breakingbad.characters.domain.usecase

import es.rufflecol.sam.breakingbad.characters.domain.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.characters.domain.model.exception.UpdateFailedException

class UpdateCharactersUseCase(private val repository: CharactersRepository) {

    @Throws(UpdateFailedException::class)
    suspend operator fun invoke() {
        repository.update()
    }

}