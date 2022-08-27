//
//  ValidationUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension FormValidationUseCase {
    func perform(
        toValidate: FieldToValidate,
        validations: [String:ValidationState]
    ) async -> [String:ValidationState] {
        do {
            let param = FormValidationUseCase.Param(
                toValidate: toValidate,
                current: validations
            )
            let flowNative = try await asyncFunction(for: invokeNative(param: param))
            let stream = asyncStream(for: flowNative)
            for try await validations in stream {
                if let validations = validations as? [String:ValidationState] {
                    return validations
                }
            }
        } catch {
            return validations
        }
        
        return validations
    }
}
