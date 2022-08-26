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
        let nativeSuspend = await asyncResult(for: invokeNative(param: nil))
        if case let .success(nativeFlow) = nativeSuspend {
            do {
                let stream = asyncStream(for: nativeFlow)
                for try await isSignedIn in stream {
                    return isSignedIn == true
                }
            } catch {
                return false
            }
        }
        
        return false
    }
}
