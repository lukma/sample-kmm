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
                LoadMoreView(loadMoreAction: fetchArticles)
            }
            
            if uiState.hasError {
                ErrorView(errorMessage: uiState.errorMessage, retryAction: fetchArticles)
            }
        }
        .listStyle(.sidebar)
    }
}

extension HomeView {
    func fetchArticles() {
        Task {
            let paging = PagingParams<KotlinInt>(
                key: NSNumber.init(value: uiState.loadPage) as? KotlinInt,
                pageSize: 10
            )
            let param = GetArticlesUseCase.Param(paging: paging)
            let result = await CommonDependencies.shared.getArticlesUseCase.perform(param)
            switch result {
            case .success(let articles):
                uiState.articles = articles
            case .failure(let error):
                uiState.hasError = true
                uiState.errorMessage = (error as NSError).domain
            }
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
