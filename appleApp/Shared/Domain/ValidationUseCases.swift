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
        let param = FormValidationUseCase.Param(
            toValidate: toValidate,
            current: validations
        )
        let nativeSuspend = await asyncResult(for: invokeNative(param: param))
        if case let .success(nativeFlow) = nativeSuspend {
            do {
                let stream = asyncStream(for: nativeFlow)
                for try await validations in stream {
                    if let validations = validations as? [String:ValidationState] {
                        return validations
                    }
                }
            } catch {
                return validations
            }
        }
        
        return validations
    }
}
