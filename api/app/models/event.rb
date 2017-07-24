class Event < ApplicationRecord
    validates_presence_of :title, :category, :description
end
