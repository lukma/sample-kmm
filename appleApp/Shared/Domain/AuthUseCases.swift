//
//  AuthUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension IsSignedInUseCase {
    func perform() async -> Swift.Result<Bool, Error> {
        do {
            let nativeFlow = try await asyncFunction(for: invokeNative(param: nil))
            let stream = asyncStream(for: nativeFlow)
            for try await item in stream {
                guard let item = item as? Bool else { continue }
                return .success(item)
            }
        } catch {
            return .failure(error)
        }
        
        return .failure(NSError(domain: "Unexpected error", code: -1))
    }
}

extension SignInUseCase {
    func perform(_ param: SignInUseCase.Param) async -> Swift.Result<Void, Error> {
        do {
            let result = try await asyncFunction(for: invokeNative(param: param))
            if result is ResultSuccess {
                return .success(())
            } else if let errorMessage = (result as? ResultFailure)?.error.message {
                return .failure(NSError(domain: errorMessage, code: -1))
            } else {
                return .failure(NSError(domain: "Unexpected error", code: -1))
            }
        } catch {
            return .failure(error)
        }
    }
}
