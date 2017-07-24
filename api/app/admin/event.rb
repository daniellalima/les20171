ActiveAdmin.register Event do
# See permitted parameters documentation:
# https://github.com/activeadmin/activeadmin/blob/master/docs/2-resource-customization.md#setting-up-strong-parameters
#
# permit_params :list, :of, :attributes, :on, :model
#
# or
#
# permit_params do
#   permitted = [:permitted, :attributes]
#   permitted << :other if params[:action] == 'create' && current_user.admin?
#   permitted
# end

permit_params :title, :category, :description

  index do
    selectable_column
    id_column
    column :title
    column :category
    column :created_at
    actions
  end

  filter :category
  filter :created_at

  form do |f|
    f.inputs do
      f.input :title
      f.input :category
      f.input :description
    end
    f.actions
  end

end
