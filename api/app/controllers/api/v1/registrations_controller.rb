class Api::V1::RegistrationsController < Api::V1::ApiController
    skip_before_action :require_login, only: [:create], raise: false
    
    def create
        resource = User.new(name: params[:name], email: params[:email], password: params[:password])

        if resource.save
            render :status => 200,
            :json => { :success => true,
                      :info => "Registered",
                      :data => { :auth_token => resource.authentication_token }
            }
        else
            render :status => :unprocessable_entity,
            :json => { :success => false,
                        :info => resource.errors,
                        :data => {} 
            }
        end
    end
end