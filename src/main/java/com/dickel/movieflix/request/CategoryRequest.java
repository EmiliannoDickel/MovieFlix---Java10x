package com.dickel.movieflix.request;

import jakarta.validation.constraints.NotEmpty;
//para validar o @NotEmpty precisa na controler colocar o @Valid no parâmetro do método
public record CategoryRequest(@NotEmpty(message = "Nome da categoria é obrigatório.") String name) {
}
