//
//  ValidationUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension FormValidationUseCase {
    func perform(_ param: FormValidationUseCase.Param) async -> [String:ValidationState] {
        do {
            let flowNative = try await asyncFunction(for: invokeNative(param: param))
            let stream = asyncStream(for: flowNative)
            for try await validations in stream {
                if let validations = validations as? [String:ValidationState] {
                    return validations
                }
            }
        } catch {
            return param.current
        }
        
        return param.current
    }
}
