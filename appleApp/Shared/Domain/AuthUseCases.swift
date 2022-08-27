//
//  AuthUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension IsSignedInUseCase {
    func perform() async -> Bool {
        do {
            let flowNative = try await asyncFunction(for: invokeNative(param: nil))
            let stream = asyncStream(for: flowNative)
            for try await isSignedIn in stream {
                return isSignedIn == true
            }
        } catch {
            return false
        }
        
        return false
    }
}

extension SignInUseCase {
    func perform(username: String, password: String) async -> Swift.Result<Void, Error> {
        var result: Swift.Result<Void, Error> = .failure(NSError(domain: "Unexpected error", code: -1))
        
        let param = SignInUseCase.Param(
            username: username,
            password: password
        )
        let nativeSuspend = await asyncResult(for: invokeNative(param: param))
        if case let .success(nativeResult) = nativeSuspend {
            if nativeResult.isSuccess() {
                result = .success(())
            } else if let errorMessage = nativeResult.exceptionOrNull()?.message {
                result = .failure(NSError(domain: errorMessage, code: -1))
            }
        }
        
        return result
    }
}
