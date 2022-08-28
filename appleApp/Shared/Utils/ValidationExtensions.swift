//
//  ValidationExtensions.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 22/08/22.
//

import common

extension Dictionary where Value == ValidationState {
    func errorMessage(forKey: Key) -> String {
        let state = self[forKey] as? ValidationState.Invalid
        guard let error = state?.error else { return "" }
        return error.errorMessage()
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
