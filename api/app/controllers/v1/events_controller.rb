class V1::EventsController < ApiController
  before_action :set_event, only: [:show, :update, :destroy]

  # GET /events
  def index
    @events = Event.all.paginate(page: params[:page], per_page: 20)
    json_response(@events)
  end

  # POST /events
  def create
    @event = Event.create!(event_params)
    json_response(@event, :created)
  end

  # GET /events/:id
  def show
    json_response(@event)
  end

  # PUT /events/:id
  def update
    @event.update(event_params)
    head :no_content
  end

  # DELETE /events/:id
  def destroy
    @event.destroy
    head :no_content
  end

  private

  def event_params
    # whitelist params
    params.permit(:title, :category, :description)
  end

  def set_event
    @event = Event.find(params[:id])
  end

end
