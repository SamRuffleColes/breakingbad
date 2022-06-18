package es.rufflecol.sam.breakingbad.ui.characters.usecase

import es.rufflecol.sam.breakingbad.data.repository.CharactersRepository
import es.rufflecol.sam.breakingbad.domain.model.exception.UpdateFailedException

class UpdateCharactersUseCase(private val repository: CharactersRepository) {

    @Throws(UpdateFailedException::class)
    suspend operator fun invoke() {
        repository.update()
    }

}