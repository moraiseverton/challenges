Rails.application.routes.draw do
  resources :writers
  get "writers/breadcrumb" => "writers#breadcrumb"

  root :to => 'writers#index' 
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
