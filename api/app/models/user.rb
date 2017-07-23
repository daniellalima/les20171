class User < ApplicationRecord
  has_secure_password
  has_secure_token :authentication_token

  def allow_token_to_be_used_only_once
    regenerate_authentication_token
    touch(:token_created_at)
  end

  def logout
   invalidate_token
  end

  # This method is not available in has_secure_token
  def invalidate_token
    self.update_columns(authentication_token: nil)
  end

  def self.valid_login?(email, password)
    user = find_by(email: email)
    if user && user.authenticate(password)
      user
    end
  end
end
