(ns status-im.ui.screens.keycard.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [status-im.i18n :as i18n]
            [status-im.multiaccounts.core :as multiaccounts]
            [status-im.react-native.resources :as resources]
            [status-im.ui.components.colors :as colors]
            [status-im.ui.components.icons.vector-icons :as vector-icons]
            [status-im.ui.components.list-item.views :as list-item]
            [status-im.ui.components.react :as react]
            [status-im.ui.components.topbar :as topbar]
            [status-im.ui.screens.chat.photos :as photos]
            [status-im.ui.components.button :as button]
            [status-im.ui.screens.hardwallet.pin.views :as pin.views]
            [status-im.ui.screens.keycard.styles :as styles]
            [status-im.constants :as constants]
            [status-im.ui.components.button :as button]
            [status-im.hardwallet.login :as hardwallet.login]
            [status-im.ui.screens.hardwallet.frozen-card.view :as frozen-card.view])
  (:require-macros [status-im.utils.views :refer [defview letsubs]]))

;; NOTE(Ferossgp): Seems like it should be in popover
(defn blank []
  [react/view {:flex             1
               :justify-content  :center
               :align-items      :center
               :background-color colors/gray-transparent-40}
   [react/view {:background-color colors/white
                :height           433
                :width            "85%"
                :border-radius    16
                :flex-direction   :column
                :justify-content  :space-between
                :align-items      :center}
    [react/view {:margin-top         32
                 :padding-horizontal 34}
     [react/text {:style {:typography :title-bold
                          :text-align :center}}
      (i18n/label :t/blank-keycard-title)]
     [react/view {:margin-top 16}
      [react/text {:style {:color       colors/gray
                           :line-height 22
                           :text-align  :center}}
       (i18n/label :t/blank-keycard-text)]]]
    [react/view
     [react/image {:source      (resources/get-image :keycard)
                   :resize-mode :center
                   :style       {:width  144
                                 :height 114}}]]
    [react/view {:margin-bottom 32}
     [button/button
      {:on-press #(re-frame/dispatch [:navigate-back])
       :lable    (i18n/label :t/ok-got-it)}]]]])

;; NOTE(Ferossgp): Seems like it should be in popover
(defn wrong []
  [react/view {:flex             1
               :justify-content  :center
               :align-items      :center
               :background-color colors/gray-transparent-40}
   [react/view {:background-color colors/white
                :height           413
                :width            "85%"
                :border-radius    16
                :flex-direction   :column
                :justify-content  :space-between
                :align-items      :center}
    [react/view {:margin-top         32
                 :padding-horizontal 34}
     [react/text {:style {:typography :title-bold
                          :text-align :center}}
      (i18n/label :t/wrong-keycard-title)]
     [react/view {:margin-top 16}
      [react/text {:style {:color       colors/gray
                           :line-height 22
                           :text-align  :center}}
       (i18n/label :t/wrong-keycard-text)]]]
    [react/view
     [react/image {:source (resources/get-image :keycard-wrong)
                   :style  {:width  255
                            :height 124}}]]
    [react/view {:margin-bottom 32}
     [button/button
      {:on-press #(re-frame/dispatch [:navigate-back])
       :label    (i18n/label :t/ok-got-it)}]]]])

(defn unpaired []
  [react/view {:flex             1
               :justify-content  :center
               :align-items      :center
               :background-color colors/gray-transparent-40}
   [react/view {:background-color colors/white
                :height           433
                :width            "85%"
                :border-radius    16
                :flex-direction   :column
                :justify-content  :space-between
                :align-items      :center}
    [react/view {:margin-top         32
                 :padding-horizontal 34}
     [react/text {:style {:typography :title-bold
                          :text-align :center}}
      (i18n/label :t/unpaired-keycard-title)]
     [react/view {:margin-top 16}
      [react/text {:style {:color       colors/gray
                           :line-height 22
                           :text-align  :center}}
       (i18n/label :t/unpaired-keycard-text)]]]
    [react/view
     [react/image {:source (resources/get-image :keycard-wrong)
                   :style  {:width  255
                            :height 124}}]]
    [react/view {:margin-bottom  32
                 :flex-direction :column
                 :align-items    :center}
     [button/button
      {:on-press #(re-frame/dispatch [:keycard.login.ui/pair-card-pressed])
       :label    (i18n/label :t/pair-this-card)}]
     [react/view {:margin-top 27}
      [button/button
       {:type     :secondary
        :on-press #(re-frame/dispatch [:keycard.login.ui/dismiss-pressed])
        :label    (i18n/label :t/dismiss)}]]]]])

;; NOTE(Ferossgp): Seems like it should be in popover
(defn not-keycard []
  [react/view {:flex             1
               :justify-content  :center
               :align-items      :center
               :background-color colors/gray-transparent-40}
   [react/view {:background-color colors/white
                :height           453
                :width            "85%"
                :border-radius    16
                :flex-direction   :column
                :justify-content  :space-between
                :align-items      :center}
    [react/view {:margin-top 32}
     [react/text {:style {:typography :title-bold
                          :text-align :center}}
      (i18n/label :t/not-keycard-title)]
     [react/view {:margin-top         16
                  :padding-horizontal 38}
      [react/text {:style {:color       colors/gray
                           :line-height 22
                           :text-align  :center}}
       (i18n/label :t/not-keycard-text)]]]
    [react/view {:margin-top  16
                 :align-items :center}
     [react/image {:source (resources/get-image :not-keycard)
                   :style  {:width  144
                            :height 120}}]
     [react/view {:margin-top 40}
      [react/touchable-highlight {:on-press #(.openURL ^js react/linking
                                                       constants/keycard-integration-link)}
       [react/view {:flex-direction  :row
                    :align-items     :center
                    :justify-content :center}
        [react/text {:style {:text-align :center
                             :color      colors/blue}}
         (i18n/label :t/learn-more-about-keycard)]
        [vector-icons/tiny-icon :tiny-icons/tiny-external {:color           colors/blue
                                                           :container-style {:margin-left 5}}]]]]]
    [react/view {:margin-bottom 32}
     [button/button {:on-press #(re-frame/dispatch [:navigate-back])
                     :label    (i18n/label :t/ok-got-it)}]]]])

(defn photo [_ _]
  (reagent/create-class
   {:should-component-update
    (fn [_ [_ old-account] [_ new-account]]
      (and (not (nil? new-account))
           (and (not (:photo-path old-account))
                (nil? (:photo-path new-account)))))

    :reagent-render
    (fn [account small-screen?]
      ;;TODO this should be done in a subscription
      [photos/photo (multiaccounts/displayed-photo account)
       {:size (if small-screen? 45 61)}])}))

(defn access-is-reset [{:keys [hide-login-actions?]}]
  [react/view
   {:style {:flex 1
            :align-items :center}}
   [react/view
    {:style {:flex 1
             :align-items     :center
             :justify-content :center}}
    [react/view
     {:style
      {:background-color colors/green-transparent-10
       :margin-bottom 32
       :width            40
       :height           40
       :align-items      :center
       :justify-content  :center
       :border-radius    20}}
     [vector-icons/icon
      :main-icons/check
      {:color colors/green}]]
    [react/text {:style {:typography :header}}
     (i18n/label :t/keycard-access-reset)]
    [react/text (i18n/label :t/keycard-can-use-with-new-passcode)]]
   (when-not hide-login-actions?
     [react/view
      {:style {:width 160
               :margin-bottom 15}}
      [button/button
       {:type            :main
        :style           {:align-self :stretch}
        :container-style {:height 52}
        :label           (i18n/label :t/open)
        :on-press        #(re-frame/dispatch
                           [::hardwallet.login/login-after-reset])}]])])

(defn frozen-card []
  [frozen-card.view/frozen-card
   {:show-dismiss-button? false}])

(defn blocked-card []
  [react/view {:style {:flex        1
                       :align-items :center}}
   [react/view {:margin-top        24
                :margin-horizontal 24
                :align-items       :center}
    [react/view {:background-color colors/red-transparent-10
                 :width            32
                 :height           32
                 :border-radius    16
                 :align-items      :center
                 :justify-content  :center}
     [vector-icons/icon
      :main-icons/cancel
      {:color  colors/red
       :width  20
       :height 20}]]
    [react/text {:style {:typography    :title-bold
                         :margin-top    16
                         :margin-bottom 8}}
     (i18n/label :t/keycard-is-blocked-title)]
    [react/text {:style {:color      colors/gray
                         :text-align :center}}
     (i18n/label :t/keycard-is-blocked-details)]
    [react/text "\n"]
    [react/nested-text
     {:style {:color      colors/gray
              :text-align :center}}
     (i18n/label :t/keycard-is-blocked-instructions)
     [{} " "]
     [{:style    {:color colors/blue}
       :on-press #(.openURL ^js react/linking "https://status.im/faq/#keycard")}
      (i18n/label :t/learn-more)]]]])

(defn- step-view [step]
  [react/view
   {:style {:flex            1
            :justify-content :center
            :align-items     :center}}
   [react/text {:style {:typography :title-bold :text-align :center}}
    (i18n/label :t/keycard-reset-passcode)]
   [react/text {:style {:color colors/gray}}
    (i18n/label :t/keycard-enter-new-passcode {:step step})]])

(defview login-pin [{:keys [back-button-handler
                            hide-login-actions?
                            default-enter-step]
                     :or {default-enter-step :login}}]
  (letsubs [pin [:hardwallet/pin]
            enter-step [:hardwallet/pin-enter-step]
            status [:hardwallet/pin-status]
            error-label [:hardwallet/pin-error-label]
            login-multiaccount [:multiaccounts/login]
            multiaccount [:multiaccount]
            small-screen? [:dimensions/small-screen?]
            retry-counter [:hardwallet/retry-counter]]
    (let [{:keys [name] :as account} (or login-multiaccount multiaccount)
          ;; TODO(rasom): this hack fixes state mess when more then two
          ;; pin-view instances are used at the same time. Should be properly
          ;; refactored instead
          enter-step (or enter-step default-enter-step)]
      [react/view styles/container
       [topbar/topbar
        {:accessories [(when-not hide-login-actions?
                         {:icon    :main-icons/more
                          :handler #(re-frame/dispatch [:keycard.login.pin.ui/more-icon-pressed])})]
         :content (cond
                    (= :reset enter-step)
                    [step-view 1]

                    (= :reset-confirmation enter-step)
                    [step-view 2]

                    (and (= :puk enter-step)
                         (not= :blocked-card status))
                    [react/view
                     {:style {:flex            1
                              :justify-content :center
                              :align-items     :center}}
                     [react/text {:style {:color colors/gray}}
                      (i18n/label :t/enter-puk-code)]])
         :navigation
         {:icon                :main-icons/arrow-left
          :accessibility-label :back-button
          :handler             #(re-frame/dispatch
                                 [(or back-button-handler
                                      :keycard.login.pin.ui/cancel-pressed)])}}]
       [react/view {:flex            1
                    :flex-direction  :column
                    :justify-content :space-between
                    :align-items     :center}
        [react/view {:flex-direction  :column
                     :justify-content :center
                     :align-items     :center
                     :height          140}
         [react/view {:margin-horizontal 16
                      :flex-direction    :column}
          [react/view {:justify-content :center
                       :align-items     :center
                       :flex-direction  :row}
           [react/view {:width           (if small-screen? 50 69)
                        :height          (if small-screen? 50 69)
                        :justify-content :center
                        :align-items     :center}
            [photo account small-screen?]
            [react/view {:justify-content  :center
                         :align-items      :center
                         :width            (if small-screen? 18 24)
                         :height           (if small-screen? 18 24)
                         :border-radius    (if small-screen? 18 24)
                         :position         :absolute
                         :right            0
                         :bottom           0
                         :background-color colors/white
                         :border-width     1
                         :border-color     colors/black-transparent}
             [react/image {:source (resources/get-image :keycard-key)
                           :style  {:width  (if small-screen? 6 8)
                                    :height (if small-screen? 11 14)}}]]]]
          [react/text {:style           {:text-align  :center
                                         :margin-top  (if small-screen? 8 12)
                                         :color       colors/black
                                         :font-weight "500"}
                       :number-of-lines 1
                       :ellipsize-mode  :middle}
           name]]]
        (cond
          (= :after-unblocking status)
          [access-is-reset
           {:hide-login-actions? hide-login-actions?}]

          (= :frozen-card status)
          [frozen-card]

          (= :blocked-card status)
          [blocked-card]

          :else
          [pin.views/pin-view
           {:pin                     pin
            :retry-counter           retry-counter
            :small-screen?           small-screen?
            :status                  status
            :error-label             error-label
            :step                    enter-step
            :save-password-checkbox? (not (contains?
                                           #{:reset :reset-confirmation :puk}
                                           enter-step))}])
        (when-not hide-login-actions?
          [react/view {:margin-bottom (if small-screen? 25 32)}
           [react/touchable-highlight
            {:on-press #(re-frame/dispatch [:multiaccounts.recover.ui/recover-multiaccount-button-pressed])}
            [react/text {:style {:color colors/blue}}
             (i18n/label :t/recover-key)]]])]])))

(defn- more-sheet-content []
  [react/view {:flex 1}
   [list-item/list-item
    {:theme     :action
     :title     :t/create-new-key
     :icon      :main-icons/profile
     :on-press  #(re-frame/dispatch [:multiaccounts.create.ui/get-new-key])}]])

(def more-sheet
  {:content        more-sheet-content
   :content-height 65})
