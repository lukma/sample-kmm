//
//  ValidationExt.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 21/08/22.
//

import common

extension Dictionary where Value == ValidationState {
    func errorMessage(forKey: Key) -> String {
        let state = self[forKey] as? ValidationState.Invalid
        if state != nil {
            return state!.error.errorMessage()
        } else {
            return ""
        }
    }
}

extension ValidationError {
    func errorMessage() -> String {
        if self is ValidationError.FieldBlank {
            return "Field can't be blank"
        } else {
            return "Unexpected error"
        }
    }
}
