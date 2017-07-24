class EventSerializer < ActiveModel::Serializer
  attributes :id, :title, :description, :category, :created_at, :updated_at
end
