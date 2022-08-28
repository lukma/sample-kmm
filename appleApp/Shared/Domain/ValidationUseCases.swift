//
//  ValidationUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension FormValidationUseCase {
    func perform(_ param: FormValidationUseCase.Param) async -> Swift.Result<[String:ValidationState], Error> {
        do {
            let nativeFlow = try await asyncFunction(for: invokeNative(param: param))
            let stream = asyncStream(for: nativeFlow)
            for try await item in stream {
                guard let item = item as? [String:ValidationState] else { continue }
                return .success(item)
            }
        } catch {
            return .failure(error)
        }
        
        return .failure(NSError(domain: "Unexpected error", code: -1))
    }
}
