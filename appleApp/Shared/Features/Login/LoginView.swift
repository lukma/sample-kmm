//
//  LoginView.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 21/08/22.
//

import SwiftUI
import common

struct LoginView: View {
    @StateObject private var uiState = LoginUiState()
    
    var body: some View {
        ZStack {
            VStack(alignment: .trailing, spacing: 16) {
                VStack(alignment: .leading) {
                    HStack {
                        Image(systemName: "person.fill").foregroundColor(.gray)
                        TextField("Username", text: $uiState.username)
                            .onChange(of: uiState.username, perform: { validate(.username, value: $0) })
                            .autocapitalization(.none)
                    }
                    .padding()
                    .overlay(RoundedRectangle(cornerRadius: 5).stroke(.gray, lineWidth: 1))
                    
                    Text(uiState.validations.errorMessage(forKey: .username))
                        .foregroundColor(.red)
                }
                
                
                VStack(alignment: .leading) {
                    HStack {
                        Image(systemName: "lock.fill").foregroundColor(.gray)
                        SecureField("Password", text: $uiState.password)
                            .onChange(of: uiState.password, perform: { validate(.password, value: $0) })
                    }
                    .padding()
                    .overlay(RoundedRectangle(cornerRadius: 5).stroke(.gray, lineWidth: 1))
                    
                    Text(uiState.validations.errorMessage(forKey: .password))
                        .foregroundColor(.red)
                }

                Button("Sign In", action: signIn)
                    .buttonStyle(.borderedProminent)
                    .alert(uiState.errorMessage, isPresented: $uiState.isError) {
                        Button("Retry", action: signIn)
                    }
            }
            .padding()
            
            Color.black
                .opacity(uiState.isLoading ? 0.3 : 0)
                .edgesIgnoringSafeArea(.all)
            
            ProgressView()
                .opacity(uiState.isLoading ? 1 : 0)
        }
    }
}

extension LoginView {
    private func validate(_ field: LoginFormSpec, value: String) {
        let toValidate = FieldToValidate(
            key: field.rawValue,
            rules: field.rules(),
            value: value
        )
        let validations = [String:ValidationState](
            uniqueKeysWithValues: uiState.validations.map { (key, value) in (key.rawValue, value) }
        )
        let param = FormValidationUseCase.Param(
            toValidate: toValidate,
            current: validations
        )
        // Todo - invoke FormValidationUseCase
    }
    
    private func signIn() {
        uiState.isLoading = true
        uiState.isError = false
        uiState.errorMessage = ""
        
        let param = SignInUseCase.Param(
            username: uiState.username,
            password: uiState.password
        )
        // Todo - invoke SignInUseCase
    }
}

class LoginUiState: ObservableObject {
    @Published var username = ""
    @Published var password = ""
    @Published var isLoading = false
    @Published var isError = false
    @Published var errorMessage = ""
    @Published var validations: [LoginFormSpec:ValidationState] = [
        .username: ValidationState.None(),
        .password: ValidationState.None()
    ]
}

enum LoginFormSpec: String {
    case username = "username"
    case password = "password"
    
    func rules() -> [FieldRule] {
        switch self {
        case .username:
            return [FieldRule.NoFieldBlank()]
        case .password:
            return [FieldRule.NoFieldBlank()]
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
