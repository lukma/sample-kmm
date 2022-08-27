//
//  ArticleUseCases.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 27/08/22.
//

import KMPNativeCoroutinesAsync
import common

extension GetArticlesUseCase {
    func perform(_ param: GetArticlesUseCase.Param) async -> Swift.Result<[Article], Error> {
        do {
            let flowNative = try await asyncFunction(for: invokeNative(param: param))
            let stream = asyncStream(for: flowNative)
            for try await paging in stream {
                var articles: [Article] = []
                
                guard let items = paging?.items else { continue }
                for item in items {
                    guard let article = item as? Article else { continue }
                    articles.append(article)
                }
                
                return .success(articles)
            }
        } catch {
            return .failure(error)
        }
        
        return .failure(NSError(domain: "Unexpected error", code: -1))
    }
}
