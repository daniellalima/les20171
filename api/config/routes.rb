Rails.application.routes.draw do


  namespace :api do
    namespace :v1 do
      post 'registrations' => 'registrations#create', :as => 'register'
      resources :sessions, only: [:create, :destroy]
    end
  end

end
