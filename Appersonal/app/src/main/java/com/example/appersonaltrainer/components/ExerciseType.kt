package com.example.appersonaltrainer.components

enum class ExerciseType {
    CAMINHADA {
        override fun toString(): String {
            return "CAMINHADA"
        }
    },
    CORRIDA {
        override fun toString(): String {
            return "CORRIDA"
        }
    },
    YOGA {
        override fun toString(): String {
            return "YOGA"
        }
    },
    CICLISMO {
        override fun toString(): String {
            return "CICLISMO"
        }
    }
}
