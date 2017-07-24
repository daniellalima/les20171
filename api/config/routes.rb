Rails.application.routes.draw do
  
  devise_for :admin_users, ActiveAdmin::Devise.config
  ActiveAdmin.routes(self)
  scope module: :v1, constraints: ApiVersion.new('v1', true) do
    resources :events
  end

  post 'auth/login', to: 'authentication#authenticate'
  post 'signup', to: 'users#create'
end