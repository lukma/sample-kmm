//
//  ArticleUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension GetArticlesUseCase {
    func perform(paging: PagingParams<KotlinInt>) async -> Swift.Result<[Article], Error> {
        let param = GetArticlesUseCase.Param(paging: paging)
        let nativeSuspend = await asyncResult(for: invokeNative(param: param))
        if case let .success(nativeFlow) = nativeSuspend {
            do {
                let stream = asyncStream(for: nativeFlow)
                for try await paging in stream {
                    if let items = paging?.items {
                        var articles: [Article] = []
                        for item in items {
                            guard let article = item as? Article else { continue }
                            articles.append(article)
                        }
                        return .success(articles)
                    }
                }
            } catch {
                return .failure(error)
            }
        }
        
        return .failure(NSError(domain: "Unexpected error", code: -1))
    }
}
