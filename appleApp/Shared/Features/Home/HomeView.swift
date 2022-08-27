//
//  HomeView.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 22/08/22.
//

import SwiftUI
import common

struct HomeView: View {
    @StateObject private var uiState = HomeUiState()
    
    var body: some View {
        List {
            ForEach(uiState.articles, id: \.self.id) { article in
                ArticleItem(article: article)
            }
            
            if uiState.hasNextPage {
                LoadMoreView()
                    .task {
                        await fetchArticles()
                    }
            }
            
            if uiState.hasError {
                ErrorView(errorMessage: uiState.errorMessage, retryAction: {
                    Task {
                        await fetchArticles()
                    }
                })
            }
        }
        .listStyle(.sidebar)
    }
}

extension HomeView {
    func fetchArticles() async {
        uiState.hasError = false
        uiState.errorMessage = ""
        
        let paging = PagingParams<KotlinInt>(
            key: NSNumber.init(value: uiState.loadPage) as? KotlinInt,
            pageSize: 10
        )
        let param = GetArticlesUseCase.Param(paging: paging)
        let result = await CommonDependencies.shared.getArticlesUseCase.perform(param)
        switch result {
        case .success(let paging):
            if let articles = paging.items as? [Article] {
                for article in articles {
                    uiState.articles.append(article)
                }
            }
            
            if let total = (paging as? PagingResultNetwork)?.total {
                uiState.hasNextPage = uiState.articles.count < total
            } else {
                uiState.hasNextPage = false
            }
            
            if uiState.hasNextPage {
                uiState.loadPage += 1
            }
        case .failure(let error):
            uiState.hasNextPage = false
            uiState.hasError = true
            uiState.errorMessage = (error as NSError).domain
        }
    }
}

class HomeUiState: ObservableObject {
    @Published var articles = [Article]()
    @Published var loadPage = 1
    @Published var hasNextPage = true
    @Published var hasError = false
    @Published var errorMessage = ""
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
